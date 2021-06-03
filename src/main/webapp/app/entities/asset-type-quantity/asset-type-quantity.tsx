import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './asset-type-quantity.reducer';
import { IAssetTypeQuantity } from 'app/shared/model/asset-type-quantity.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAssetTypeQuantityProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const AssetTypeQuantity = (props: IAssetTypeQuantityProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { assetTypeQuantityList, match, loading } = props;
  return (
    <div>
      <h2 id="asset-type-quantity-heading" data-cy="AssetTypeQuantityHeading">
        Asset Type Quantities
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Asset Type Quantity
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {assetTypeQuantityList && assetTypeQuantityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Quantity</th>
                <th>Owned Assets</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {assetTypeQuantityList.map((assetTypeQuantity, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${assetTypeQuantity.id}`} color="link" size="sm">
                      {assetTypeQuantity.id}
                    </Button>
                  </td>
                  <td>{assetTypeQuantity.quantity}</td>
                  <td>
                    {assetTypeQuantity.ownedAssets ? (
                      <Link to={`organisation/${assetTypeQuantity.ownedAssets.id}`}>{assetTypeQuantity.ownedAssets.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${assetTypeQuantity.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${assetTypeQuantity.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${assetTypeQuantity.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Asset Type Quantities found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ assetTypeQuantity }: IRootState) => ({
  assetTypeQuantityList: assetTypeQuantity.entities,
  loading: assetTypeQuantity.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AssetTypeQuantity);
