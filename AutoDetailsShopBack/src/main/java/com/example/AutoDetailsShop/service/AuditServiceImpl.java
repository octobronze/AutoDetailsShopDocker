package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Audit;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;
import com.example.AutoDetailsShop.repos.AuditRepo;
import com.example.AutoDetailsShop.security.JwtTokenProvider;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service("auditServiceImpl")
public class AuditServiceImpl implements AuditService {
    private final AuditRepo auditRepo;
    private final JwtTokenProvider jwtTokenProvider;

    public AuditServiceImpl(AuditRepo auditRepo, JwtTokenProvider jwtTokenProvider) {
        this.auditRepo = auditRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void delete(Long id) throws ValidationException, NotFoundException {
        if(id == null){
            throw new ValidationException("Id is null");
        }
        Audit audit = auditRepo.findById(id).orElseThrow(() -> new NotFoundException("Audit was not found"));
        auditRepo.delete(audit);
    }

    @Override
    public List<Audit> getAll() {
        return auditRepo.findAll();
    }

    @Override
    public Audit getById(Long id) throws ValidationException, NotFoundException {
        if(id == null){
            throw new ValidationException("Id is null");
        }
        return auditRepo.findById(id).orElseThrow(() -> new NotFoundException("Audit was not found"));
    }

    @Override
    @Async
    public void add(String requestType, String link, HttpServletRequest request) throws ValidationException {
        Audit audit = new Audit();
        audit.setRequest(requestType);
        audit.setLink(link);
        audit.setInteractionTime(new Date());
        audit.setUser(jwtTokenProvider.getUserFromRequest(request));
        auditRepo.save(audit);
        //return CompletableFuture.completedFuture(null);
    }
}
