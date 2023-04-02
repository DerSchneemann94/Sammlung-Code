import { SetMetadata } from "@nestjs/common";


//TO-DO: funktioniert noch nicht daher alle decorator händisch einfügen
export const Public = () => SetMetadata( "isPublic", true );