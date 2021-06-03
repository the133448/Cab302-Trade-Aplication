import { IUser } from 'app/shared/model/user.model';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { ITrade } from 'app/shared/model/trade.model';

export interface IApplicationUser {
  id?: number;
  internalUser?: IUser | null;
  organisation?: IOrganisation | null;
  users?: ITrade[] | null;
}

export const defaultValue: Readonly<IApplicationUser> = {};
