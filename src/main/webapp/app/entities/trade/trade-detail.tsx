import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './trade.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITradeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TradeDetail = (props: ITradeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { tradeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tradeDetailsHeading">Trade</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tradeEntity.id}</dd>
          <dt>
            <span id="tradeStatus">Trade Status</span>
          </dt>
          <dd>{tradeEntity.tradeStatus}</dd>
          <dt>
            <span id="tradeType">Trade Type</span>
          </dt>
          <dd>{tradeEntity.tradeType}</dd>
          <dt>
            <span id="assetQty">Asset Qty</span>
          </dt>
          <dd>{tradeEntity.assetQty}</dd>
          <dt>
            <span id="credits">Credits</span>
          </dt>
          <dd>{tradeEntity.credits}</dd>
          <dt>Asset Type</dt>
          <dd>{tradeEntity.assetType ? tradeEntity.assetType.id : ''}</dd>
          <dt>Organisation</dt>
          <dd>{tradeEntity.organisation ? tradeEntity.organisation.id : ''}</dd>
          <dt>Application User</dt>
          <dd>{tradeEntity.applicationUser ? tradeEntity.applicationUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/trade" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trade/${tradeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ trade }: IRootState) => ({
  tradeEntity: trade.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TradeDetail);
