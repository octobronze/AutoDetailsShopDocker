import { DetailsInfo } from "./details-info";

export class DetailsInfoStorage{
    details: DetailsInfo[];

    constructor(details: DetailsInfo[]){
        this.details = details;
    }

    getByID(id: number){
        for(var i = 0; i < this.details.length; i ++){
            if(this.details[i].id == id){
                return this.details[i];
            }
        }
        return null;
    }

    getByName(name: string){
        for(var i = 0; i < this.details.length; i ++){
            if(this.details[i].detailName === name){
                return this.details[i];
            }
        }
        return null;
    }
}