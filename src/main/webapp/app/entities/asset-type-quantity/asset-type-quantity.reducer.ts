import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAssetTypeQuantity, defaultValue } from 'app/shared/model/asset-type-quantity.model';

export const ACTION_TYPES = {
  FETCH_ASSETTYPEQUANTITY_LIST: 'assetTypeQuantity/FETCH_ASSETTYPEQUANTITY_LIST',
  FETCH_ASSETTYPEQUANTITY: 'assetTypeQuantity/FETCH_ASSETTYPEQUANTITY',
  CREATE_ASSETTYPEQUANTITY: 'assetTypeQuantity/CREATE_ASSETTYPEQUANTITY',
  UPDATE_ASSETTYPEQUANTITY: 'assetTypeQuantity/UPDATE_ASSETTYPEQUANTITY',
  PARTIAL_UPDATE_ASSETTYPEQUANTITY: 'assetTypeQuantity/PARTIAL_UPDATE_ASSETTYPEQUANTITY',
  DELETE_ASSETTYPEQUANTITY: 'assetTypeQuantity/DELETE_ASSETTYPEQUANTITY',
  RESET: 'assetTypeQuantity/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAssetTypeQuantity>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AssetTypeQuantityState = Readonly<typeof initialState>;

// Reducer

export default (state: AssetTypeQuantityState = initialState, action): AssetTypeQuantityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ASSETTYPEQUANTITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ASSETTYPEQUANTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ASSETTYPEQUANTITY):
    case REQUEST(ACTION_TYPES.UPDATE_ASSETTYPEQUANTITY):
    case REQUEST(ACTION_TYPES.DELETE_ASSETTYPEQUANTITY):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ASSETTYPEQUANTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ASSETTYPEQUANTITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ASSETTYPEQUANTITY):
    case FAILURE(ACTION_TYPES.CREATE_ASSETTYPEQUANTITY):
    case FAILURE(ACTION_TYPES.UPDATE_ASSETTYPEQUANTITY):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ASSETTYPEQUANTITY):
    case FAILURE(ACTION_TYPES.DELETE_ASSETTYPEQUANTITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ASSETTYPEQUANTITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ASSETTYPEQUANTITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ASSETTYPEQUANTITY):
    case SUCCESS(ACTION_TYPES.UPDATE_ASSETTYPEQUANTITY):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ASSETTYPEQUANTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ASSETTYPEQUANTITY):
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

const apiUrl = 'api/asset-type-quantities';

// Actions

export const getEntities: ICrudGetAllAction<IAssetTypeQuantity> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ASSETTYPEQUANTITY_LIST,
  payload: axios.get<IAssetTypeQuantity>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAssetTypeQuantity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ASSETTYPEQUANTITY,
    payload: axios.get<IAssetTypeQuantity>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAssetTypeQuantity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ASSETTYPEQUANTITY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAssetTypeQuantity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ASSETTYPEQUANTITY,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAssetTypeQuantity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ASSETTYPEQUANTITY,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAssetTypeQuantity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ASSETTYPEQUANTITY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
