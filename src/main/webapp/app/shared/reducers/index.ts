import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import assetType, {
  AssetTypeState
} from 'app/entities/asset-type/asset-type.reducer';
// prettier-ignore
import assetTypeQuantity, {
  AssetTypeQuantityState
} from 'app/entities/asset-type-quantity/asset-type-quantity.reducer';
// prettier-ignore
import applicationUser, {
  ApplicationUserState
} from 'app/entities/application-user/application-user.reducer';
// prettier-ignore
import organisation, {
  OrganisationState
} from 'app/entities/organisation/organisation.reducer';
// prettier-ignore
import trade, {
  TradeState
} from 'app/entities/trade/trade.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly assetType: AssetTypeState;
  readonly assetTypeQuantity: AssetTypeQuantityState;
  readonly applicationUser: ApplicationUserState;
  readonly organisation: OrganisationState;
  readonly trade: TradeState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  assetType,
  assetTypeQuantity,
  applicationUser,
  organisation,
  trade,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
