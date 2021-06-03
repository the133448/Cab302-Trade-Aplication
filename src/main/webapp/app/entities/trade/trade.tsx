import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './trade.reducer';
import { ITrade } from 'app/shared/model/trade.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITradeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Trade = (props: ITradeProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { tradeList, match, loading } = props;
  return (
    <div>
      <h2 id="trade-heading" data-cy="TradeHeading">
        Trades
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Trade
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tradeList && tradeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Trade Status</th>
                <th>Trade Type</th>
                <th>Asset Qty</th>
                <th>Credits</th>
                <th>Asset Type</th>
                <th>Organisation</th>
                <th>Application User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tradeList.map((trade, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${trade.id}`} color="link" size="sm">
                      {trade.id}
                    </Button>
                  </td>
                  <td>{trade.tradeStatus}</td>
                  <td>{trade.tradeType}</td>
                  <td>{trade.assetQty}</td>
                  <td>{trade.credits}</td>
                  <td>{trade.assetType ? <Link to={`asset-type/${trade.assetType.id}`}>{trade.assetType.id}</Link> : ''}</td>
                  <td>{trade.organisation ? <Link to={`organisation/${trade.organisation.id}`}>{trade.organisation.id}</Link> : ''}</td>
                  <td>
                    {trade.applicationUser ? (
                      <Link to={`application-user/${trade.applicationUser.id}`}>{trade.applicationUser.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${trade.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${trade.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${trade.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Trades found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ trade }: IRootState) => ({
  tradeList: trade.entities,
  loading: trade.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Trade);
