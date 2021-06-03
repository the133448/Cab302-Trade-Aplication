import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Trade from './trade';
import TradeDetail from './trade-detail';
import TradeUpdate from './trade-update';
import TradeDeleteDialog from './trade-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TradeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TradeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TradeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Trade} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TradeDeleteDialog} />
  </>
);

export default Routes;
