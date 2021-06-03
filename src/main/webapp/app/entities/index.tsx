import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AssetType from './asset-type';
import AssetTypeQuantity from './asset-type-quantity';
import ApplicationUser from './application-user';
import Organisation from './organisation';
import Trade from './trade';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}asset-type`} component={AssetType} />
      <ErrorBoundaryRoute path={`${match.url}asset-type-quantity`} component={AssetTypeQuantity} />
      <ErrorBoundaryRoute path={`${match.url}application-user`} component={ApplicationUser} />
      <ErrorBoundaryRoute path={`${match.url}organisation`} component={Organisation} />
      <ErrorBoundaryRoute path={`${match.url}trade`} component={Trade} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
