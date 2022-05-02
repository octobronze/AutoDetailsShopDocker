import { CarModelsInfo } from "./carModel-info";

export class CarModelsInfoStorage{
    carModels: CarModelsInfo[];

    constructor(carModels: CarModelsInfo[]){
        this.carModels = carModels;
    }

    getByID(id: number){
        for(var i = 0; i < this.carModels.length; i ++){
            if(this.carModels[i].id == id){
                return this.carModels[i];
            }
        }
        return null;
    }

    getByName(name: string){
        for(var i = 0; i < this.carModels.length; i ++){
            if(this.carModels[i].carModelName === name){
                return this.carModels[i];
            }
        }
        return null;
    }
}