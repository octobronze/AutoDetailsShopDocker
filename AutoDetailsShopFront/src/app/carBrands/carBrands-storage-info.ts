import { CarBrandsInfo } from "./carBrand-info";

export class CarBrandsInfoStorage{
    carBrands: CarBrandsInfo[];

    constructor(carBrands: CarBrandsInfo[]){
        this.carBrands = carBrands;
    }

    getByID(id: number){
        for(var i = 0; i < this.carBrands.length; i ++){
            if(this.carBrands[i].id == id){
                return this.carBrands[i];
            }
        }
        return null;
    }

    getByName(name: string){
        for(var i = 0; i < this.carBrands.length; i ++){
            if(this.carBrands[i].carBrandName === name){
                return this.carBrands[i];
            }
        }

        return null;
    }
}