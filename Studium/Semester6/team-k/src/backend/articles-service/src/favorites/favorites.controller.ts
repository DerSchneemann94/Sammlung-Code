import { Controller } from "@nestjs/common";
import { EventPattern } from "@nestjs/microservices";
import { FavoritesDto } from "./dto/favorites.dto";
import { FavoritesService } from "./favorites.service";

@Controller('')
export class FavoritesController{
    constructor(private favoritesService: FavoritesService){}

    @EventPattern('adjust_User_Favorites')
    adjustUserFavorites(favoritesDto: FavoritesDto){
        return this.favoritesService.adjustUserFavorites(favoritesDto);
    }
    
}