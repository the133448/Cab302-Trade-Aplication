import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITrade, defaultValue } from 'app/shared/model/trade.model';

export const ACTION_TYPES = {
  FETCH_TRADE_LIST: 'trade/FETCH_TRADE_LIST',
  FETCH_TRADE: 'trade/FETCH_TRADE',
  CREATE_TRADE: 'trade/CREATE_TRADE',
  UPDATE_TRADE: 'trade/UPDATE_TRADE',
  PARTIAL_UPDATE_TRADE: 'trade/PARTIAL_UPDATE_TRADE',
  DELETE_TRADE: 'trade/DELETE_TRADE',
  RESET: 'trade/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITrade>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TradeState = Readonly<typeof initialState>;

// Reducer

export default (state: TradeState = initialState, action): TradeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TRADE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TRADE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TRADE):
    case REQUEST(ACTION_TYPES.UPDATE_TRADE):
    case REQUEST(ACTION_TYPES.DELETE_TRADE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TRADE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TRADE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TRADE):
    case FAILURE(ACTION_TYPES.CREATE_TRADE):
    case FAILURE(ACTION_TYPES.UPDATE_TRADE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TRADE):
    case FAILURE(ACTION_TYPES.DELETE_TRADE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRADE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRADE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TRADE):
    case SUCCESS(ACTION_TYPES.UPDATE_TRADE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TRADE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TRADE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/trades';

// Actions

export const getEntities: ICrudGetAllAction<ITrade> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TRADE_LIST,
  payload: axios.get<ITrade>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITrade> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TRADE,
    payload: axios.get<ITrade>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITrade> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TRADE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITrade> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TRADE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITrade> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TRADE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITrade> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TRADE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
