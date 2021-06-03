import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAssetType } from 'app/shared/model/asset-type.model';
import { getEntities as getAssetTypes } from 'app/entities/asset-type/asset-type.reducer';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { getEntities as getApplicationUsers } from 'app/entities/application-user/application-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './trade.reducer';
import { ITrade } from 'app/shared/model/trade.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITradeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TradeUpdate = (props: ITradeUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { tradeEntity, assetTypes, organisations, applicationUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/trade');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAssetTypes();
    props.getOrganisations();
    props.getApplicationUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...tradeEntity,
        ...values,
        assetType: assetTypes.find(it => it.id.toString() === values.assetTypeId.toString()),
        organisation: organisations.find(it => it.id.toString() === values.organisationId.toString()),
        applicationUser: applicationUsers.find(it => it.id.toString() === values.applicationUserId.toString()),
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
          <h2 id="jhipsterApp.trade.home.createOrEditLabel" data-cy="TradeCreateUpdateHeading">
            Create or edit a Trade
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : tradeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="trade-id">ID</Label>
                  <AvInput id="trade-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="tradeStatusLabel" for="trade-tradeStatus">
                  Trade Status
                </Label>
                <AvInput
                  id="trade-tradeStatus"
                  data-cy="tradeStatus"
                  type="select"
                  className="form-control"
                  name="tradeStatus"
                  value={(!isNew && tradeEntity.tradeStatus) || 'PENDING'}
                >
                  <option value="PENDING">PENDING</option>
                  <option value="PART_FULFILLED">PART_FULFILLED</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="tradeTypeLabel" for="trade-tradeType">
                  Trade Type
                </Label>
                <AvInput
                  id="trade-tradeType"
                  data-cy="tradeType"
                  type="select"
                  className="form-control"
                  name="tradeType"
                  value={(!isNew && tradeEntity.tradeType) || 'BUY'}
                >
                  <option value="BUY">BUY</option>
                  <option value="SELL">SELL</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="assetQtyLabel" for="trade-assetQty">
                  Asset Qty
                </Label>
                <AvField id="trade-assetQty" data-cy="assetQty" type="string" className="form-control" name="assetQty" />
              </AvGroup>
              <AvGroup>
                <Label id="creditsLabel" for="trade-credits">
                  Credits
                </Label>
                <AvField id="trade-credits" data-cy="credits" type="string" className="form-control" name="credits" />
              </AvGroup>
              <AvGroup>
                <Label for="trade-assetType">Asset Type</Label>
                <AvInput id="trade-assetType" data-cy="assetType" type="select" className="form-control" name="assetTypeId">
                  <option value="" key="0" />
                  {assetTypes
                    ? assetTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="trade-organisation">Organisation</Label>
                <AvInput id="trade-organisation" data-cy="organisation" type="select" className="form-control" name="organisationId">
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
              <AvGroup>
                <Label for="trade-applicationUser">Application User</Label>
                <AvInput
                  id="trade-applicationUser"
                  data-cy="applicationUser"
                  type="select"
                  className="form-control"
                  name="applicationUserId"
                >
                  <option value="" key="0" />
                  {applicationUsers
                    ? applicationUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/trade" replace color="info">
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
  assetTypes: storeState.assetType.entities,
  organisations: storeState.organisation.entities,
  applicationUsers: storeState.applicationUser.entities,
  tradeEntity: storeState.trade.entity,
  loading: storeState.trade.loading,
  updating: storeState.trade.updating,
  updateSuccess: storeState.trade.updateSuccess,
});

const mapDispatchToProps = {
  getAssetTypes,
  getOrganisations,
  getApplicationUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TradeUpdate);
