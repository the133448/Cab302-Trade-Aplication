import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './asset-type-quantity.reducer';
import { IAssetTypeQuantity } from 'app/shared/model/asset-type-quantity.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAssetTypeQuantityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AssetTypeQuantityUpdate = (props: IAssetTypeQuantityUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { assetTypeQuantityEntity, organisations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/asset-type-quantity');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getOrganisations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...assetTypeQuantityEntity,
        ...values,
        ownedAssets: organisations.find(it => it.id.toString() === values.ownedAssetsId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.assetTypeQuantity.home.createOrEditLabel" data-cy="AssetTypeQuantityCreateUpdateHeading">
            Create or edit a AssetTypeQuantity
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : assetTypeQuantityEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="asset-type-quantity-id">ID</Label>
                  <AvInput id="asset-type-quantity-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="quantityLabel" for="asset-type-quantity-quantity">
                  Quantity
                </Label>
                <AvField id="asset-type-quantity-quantity" data-cy="quantity" type="string" className="form-control" name="quantity" />
              </AvGroup>
              <AvGroup>
                <Label for="asset-type-quantity-ownedAssets">Owned Assets</Label>
                <AvInput
                  id="asset-type-quantity-ownedAssets"
                  data-cy="ownedAssets"
                  type="select"
                  className="form-control"
                  name="ownedAssetsId"
                >
                  <option value="" key="0" />
                  {organisations
                    ? organisations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/asset-type-quantity" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  organisations: storeState.organisation.entities,
  assetTypeQuantityEntity: storeState.assetTypeQuantity.entity,
  loading: storeState.assetTypeQuantity.loading,
  updating: storeState.assetTypeQuantity.updating,
  updateSuccess: storeState.assetTypeQuantity.updateSuccess,
});

const mapDispatchToProps = {
  getOrganisations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AssetTypeQuantityUpdate);
