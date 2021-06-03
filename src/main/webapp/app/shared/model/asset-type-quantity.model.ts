import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IAssetTypeQuantity {
  id?: number;
  quantity?: number | null;
  ownedAssets?: IOrganisation | null;
}

export const defaultValue: Readonly<IAssetTypeQuantity> = {};
