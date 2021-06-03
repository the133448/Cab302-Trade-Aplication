import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AssetTypeQuantity from './asset-type-quantity';
import AssetTypeQuantityDetail from './asset-type-quantity-detail';
import AssetTypeQuantityUpdate from './asset-type-quantity-update';
import AssetTypeQuantityDeleteDialog from './asset-type-quantity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AssetTypeQuantityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AssetTypeQuantityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AssetTypeQuantityDetail} />
      <ErrorBoundaryRoute path={match.url} component={AssetTypeQuantity} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AssetTypeQuantityDeleteDialog} />
  </>
);

export default Routes;
