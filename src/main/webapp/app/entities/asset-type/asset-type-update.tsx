import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAssetTypeQuantity } from 'app/shared/model/asset-type-quantity.model';
import { getEntities as getAssetTypeQuantities } from 'app/entities/asset-type-quantity/asset-type-quantity.reducer';
import { getEntity, updateEntity, createEntity, reset } from './asset-type.reducer';
import { IAssetType } from 'app/shared/model/asset-type.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAssetTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AssetTypeUpdate = (props: IAssetTypeUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { assetTypeEntity, assetTypeQuantities, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/asset-type');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAssetTypeQuantities();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...assetTypeEntity,
        ...values,
        assetType: assetTypeQuantities.find(it => it.id.toString() === values.assetTypeId.toString()),
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
          <h2 id="jhipsterApp.assetType.home.createOrEditLabel" data-cy="AssetTypeCreateUpdateHeading">
            Create or edit a AssetType
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : assetTypeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="asset-type-id">ID</Label>
                  <AvInput id="asset-type-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="asset-type-name">
                  Name
                </Label>
                <AvField id="asset-type-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label for="asset-type-assetType">Asset Type</Label>
                <AvInput id="asset-type-assetType" data-cy="assetType" type="select" className="form-control" name="assetTypeId">
                  <option value="" key="0" />
                  {assetTypeQuantities
                    ? assetTypeQuantities.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/asset-type" replace color="info">
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
  assetTypeQuantities: storeState.assetTypeQuantity.entities,
  assetTypeEntity: storeState.assetType.entity,
  loading: storeState.assetType.loading,
  updating: storeState.assetType.updating,
  updateSuccess: storeState.assetType.updateSuccess,
});

const mapDispatchToProps = {
  getAssetTypeQuantities,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AssetTypeUpdate);
