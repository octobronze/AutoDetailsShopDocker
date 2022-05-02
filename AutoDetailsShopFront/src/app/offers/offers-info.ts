import { CarBrandsInfo } from "../carBrands/carBrand-info";
import { CarModelsInfo } from "../carModels/carModel-info";
import { DetailsInfo } from "../details/details-info";

export class OffersInfo{
    id: number;
    detail: DetailsInfo;
    carBrand: CarBrandsInfo;
    carModel: CarModelsInfo;
    price: DoubleRange;

    constructor(id: number, detail: DetailsInfo, carBrand: CarBrandsInfo, carModel: CarModelsInfo, price: DoubleRange){
        this.id = id;
        this.detail = detail;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.price = price;                
    }
}