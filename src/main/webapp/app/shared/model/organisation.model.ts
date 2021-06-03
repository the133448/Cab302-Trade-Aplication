import { IAssetTypeQuantity } from 'app/shared/model/asset-type-quantity.model';
import { ITrade } from 'app/shared/model/trade.model';

export interface IOrganisation {
  id?: number;
  name?: string | null;
  credits?: number | null;
  buyOrderCredits?: number | null;
  assetTypeQuantities?: IAssetTypeQuantity[] | null;
  organisations?: ITrade[] | null;
}

export const defaultValue: Readonly<IOrganisation> = {};
