import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../auth/token-storage.service';
import { CarBrandsInfo } from '../carBrands/carBrand-info';
import { CarBrandsInfoStorage } from '../carBrands/carBrands-storage-info';
import { CarModelsInfo } from '../carModels/carModel-info';
import { CarModelsInfoStorage } from '../carModels/carModels-storage-info';
import { DetailsInfo } from '../details/details-info';
import { DetailsInfoStorage } from '../details/details-storage-info';
import { OffersInfo } from '../offers/offers-info';
import { OffersStorageInfo } from '../offers/offers-storage-info';
import { CarBrandsService } from '../services/carBrands.service';
import { CarModelsService } from '../services/carModels.service';
import { DetailsService } from '../services/details.service';
import { OffersService } from '../services/offers.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  info: any;
  offers: OffersStorageInfo | undefined;
  carBrands: CarBrandsInfoStorage | undefined;
  carModels: CarModelsInfoStorage | undefined;
  details: DetailsInfoStorage | undefined;
  carBrandsIdsCheck: string | undefined;
  carModelsIdsCheck: string | undefined;
  detailsIdsCheck: string | undefined;
  errorMessage = '';
  toggledEdit = false;
  form: any = {};
  putForm: any = {};
  filter: any = {};
  keyWord: string = '';
  editField: number = 0;
  deleteVisability: number = 0;

  constructor(private token: TokenStorageService, private offersService: OffersService, private detailsService: DetailsService, private carBrandsService: CarBrandsService, private carModelsService: CarModelsService, private http: HttpClient) { }

  ngOnInit(): void {
    
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      role: this.token.getRole()
    };

    this.offersService.getOffers().subscribe(
      data => {
        console.log(data);
        this.offers = new OffersStorageInfo(data);
        console.log(this.offers);
      },
      error => {
        console.log(error);
      }
      
    )

    this.carBrandsService.getCarBrands().subscribe(
      data => {
        console.log(data);
        this.carBrandsIdsCheck = this.createCarBrandsCheckline(data);
        this.carBrands = new CarBrandsInfoStorage(data);
        console.log(this.carBrands);
        console.log(this.carBrandsIdsCheck);
      },
      error => {
        console.log(error);
      }
    )

    this.carModelsService.getCarModels().subscribe(
      data => {
        console.log(data);
        this.carModelsIdsCheck = this.createCarModelsCheckline(data);
        this.carModels = new CarModelsInfoStorage(data);
        console.log(this.carModels);
        console.log(this.carModelsIdsCheck);
      },
      error => {
        console.log(error);
      }
    )

    this.detailsService.getDetails().subscribe(
      data => {
        console.log(data);
        this.detailsIdsCheck = this.createDetailsCheckline(data);
        this.details = new DetailsInfoStorage(data);
        console.log(this.details);
        console.log(this.detailsIdsCheck);
      },
      error => {
        console.log(error);
      }
    )

  }

  createDetailsCheckline(details: DetailsInfo[]){
    var detailsCheckLine = "";
    for(var i = 0; i < details.length; i ++){
      if(i != details.length - 1){
        detailsCheckLine += "^" + details[i].id + "$|";
      }else{
        detailsCheckLine += "^" + details[i].id;
      }
    }
    return detailsCheckLine;
  }

  createCarBrandsCheckline(carBrands: CarBrandsInfo[]){
    var carBrandsCheckLine = "";
    for(var i = 0; i < carBrands.length; i ++){
      if(i != carBrands.length - 1){
        carBrandsCheckLine += "^" + carBrands[i].id + "$|";
      }else{
        carBrandsCheckLine += "^" + carBrands[i].id + "$";
      }
    }
    return carBrandsCheckLine;
  }

  createCarModelsCheckline(carModels: CarModelsInfo[]){
    var carModelsCheckLine = "";
    for(var i = 0; i < carModels.length; i ++){
      if(i != carModels.length - 1){
        carModelsCheckLine += "^" + carModels[i].id + "$|";
      }else{
        carModelsCheckLine += "^" + carModels[i].id + "$";
      }
    }
    return carModelsCheckLine;
  }

  delete(id: number){
    const isOk = confirm("Are you sure?");
    if(isOk){
      this.offersService.removeOffer(id).subscribe(
        data => {
          console.log(id);
          this.offers?.remove(id);
          console.log(data);
        },
        error => {
          console.log(error);
        }
      )
      alert("Offer has been deleted");
    }
  }

  toggleEdit(){
    this.toggledEdit = !this.toggledEdit;
  }

  async add(){
    var detail = this.details?.getByID(this.form.detailId);
    var carBrand = this.carBrands?.getByID(this.form.carBrandId);
    var carModel = this.carModels?.getByID(this.form.carModelId);
    var price = this.form.price;
    if(detail != undefined && carBrand != undefined && carModel != undefined){
      const res = await this.offersService.addOffer(new OffersInfo(1, detail, carBrand, carModel, price)).toPromise();
      if(res != undefined){
        this.offers?.add(new OffersInfo(res.id, res.detail, res.carBrand, res.carModel, res.price));
      }
      alert("Offer has been added");
    }
  }
  
  doFilter(){
    this.keyWord = this.filter.keyWord;
  }

  setValues(id: number, detail: DetailsInfo, carBrand: CarBrandsInfo, carModel: CarModelsInfo, price: DoubleRange){
    this.editField = id;
    if(this.details?.getByName(this.putForm.detailName) != null){
      this.putForm.detailName = detail.detailName;
    }
    if(this.carBrands?.getByName(this.putForm.carBrandName) != null){
      this.putForm.carBrandName = carBrand.carBrandName;
    }
    if(this.carModels?.getByName(this.putForm.carModelName) != null){
      this.putForm.carModelName = carModel.carModelName;
    }
    this.putForm.price = price;
  }

  putEditField(id: number){
    this.hideDeleteButton();
    if(this.editField == 0){
      return;
    }
    console.log(this.editField);
    this.editField = 0;
    var detail = this.details?.getByName(this.putForm.detailName);
    var carBrand = this.carBrands?.getByName(this.putForm.carBrandName);
    var carModel = this.carModels?.getByName(this.putForm.carModelName);
    var price = this.putForm.price;
    if(detail != null && carBrand != null && carModel != null){
      const editOffer = new OffersInfo(id, detail, carBrand, carModel, price);
      console.log(editOffer);
      this.offersService.setOffer(editOffer).subscribe(
        data =>{
          this.offers?.change(editOffer);
          console.log(data);
          alert("Offer has been changed");
        },
        error =>{
          console.log(error);
        }
        
      )
    }
  }

  showDeleteButton(value: number){
    this.deleteVisability = value;
  }

  hideDeleteButton(){
    this.deleteVisability = 0;
  }

}
