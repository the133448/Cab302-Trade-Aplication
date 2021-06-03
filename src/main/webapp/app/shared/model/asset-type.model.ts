import { IAssetTypeQuantity } from 'app/shared/model/asset-type-quantity.model';
import { ITrade } from 'app/shared/model/trade.model';

export interface IAssetType {
  id?: number;
  name?: string | null;
  assetType?: IAssetTypeQuantity | null;
  assetTypes?: ITrade[] | null;
}

export const defaultValue: Readonly<IAssetType> = {};
