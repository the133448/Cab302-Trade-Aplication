import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AssetType from './asset-type';
import AssetTypeDetail from './asset-type-detail';
import AssetTypeUpdate from './asset-type-update';
import AssetTypeDeleteDialog from './asset-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AssetTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AssetTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AssetTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={AssetType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AssetTypeDeleteDialog} />
  </>
);

export default Routes;
