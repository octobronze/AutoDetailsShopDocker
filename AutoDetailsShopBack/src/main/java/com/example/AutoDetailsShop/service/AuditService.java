package com.example.AutoDetailsShop.service;

import com.example.AutoDetailsShop.domain.Audit;
import com.example.AutoDetailsShop.domain.User;
import com.example.AutoDetailsShop.exceptions.NotFoundException;
import com.example.AutoDetailsShop.exceptions.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface AuditService {

    void delete(Long id) throws ValidationException, NotFoundException;

    List<Audit> getAll();

    Audit getById(Long id) throws ValidationException, NotFoundException;

    void add(String requestType, String link, HttpServletRequest request) throws ValidationException;
}
