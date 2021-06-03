import { IAssetType } from 'app/shared/model/asset-type.model';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { TradeStatus } from 'app/shared/model/enumerations/trade-status.model';
import { TradeType } from 'app/shared/model/enumerations/trade-type.model';

export interface ITrade {
  id?: number;
  tradeStatus?: TradeStatus | null;
  tradeType?: TradeType | null;
  assetQty?: number | null;
  credits?: number | null;
  assetType?: IAssetType | null;
  organisation?: IOrganisation | null;
  applicationUser?: IApplicationUser | null;
}

export const defaultValue: Readonly<ITrade> = {};
