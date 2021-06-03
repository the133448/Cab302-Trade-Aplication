import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './asset-type-quantity.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAssetTypeQuantityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AssetTypeQuantityDetail = (props: IAssetTypeQuantityDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { assetTypeQuantityEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="assetTypeQuantityDetailsHeading">AssetTypeQuantity</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{assetTypeQuantityEntity.id}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{assetTypeQuantityEntity.quantity}</dd>
          <dt>Owned Assets</dt>
          <dd>{assetTypeQuantityEntity.ownedAssets ? assetTypeQuantityEntity.ownedAssets.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/asset-type-quantity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/asset-type-quantity/${assetTypeQuantityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ assetTypeQuantity }: IRootState) => ({
  assetTypeQuantityEntity: assetTypeQuantity.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AssetTypeQuantityDetail);
