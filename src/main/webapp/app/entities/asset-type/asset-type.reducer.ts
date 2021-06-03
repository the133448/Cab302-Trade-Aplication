import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAssetType, defaultValue } from 'app/shared/model/asset-type.model';

export const ACTION_TYPES = {
  FETCH_ASSETTYPE_LIST: 'assetType/FETCH_ASSETTYPE_LIST',
  FETCH_ASSETTYPE: 'assetType/FETCH_ASSETTYPE',
  CREATE_ASSETTYPE: 'assetType/CREATE_ASSETTYPE',
  UPDATE_ASSETTYPE: 'assetType/UPDATE_ASSETTYPE',
  PARTIAL_UPDATE_ASSETTYPE: 'assetType/PARTIAL_UPDATE_ASSETTYPE',
  DELETE_ASSETTYPE: 'assetType/DELETE_ASSETTYPE',
  RESET: 'assetType/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAssetType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AssetTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: AssetTypeState = initialState, action): AssetTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ASSETTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ASSETTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ASSETTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_ASSETTYPE):
    case REQUEST(ACTION_TYPES.DELETE_ASSETTYPE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ASSETTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ASSETTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ASSETTYPE):
    case FAILURE(ACTION_TYPES.CREATE_ASSETTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_ASSETTYPE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ASSETTYPE):
    case FAILURE(ACTION_TYPES.DELETE_ASSETTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ASSETTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ASSETTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ASSETTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_ASSETTYPE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ASSETTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ASSETTYPE):
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

const apiUrl = 'api/asset-types';

// Actions

export const getEntities: ICrudGetAllAction<IAssetType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ASSETTYPE_LIST,
  payload: axios.get<IAssetType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAssetType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ASSETTYPE,
    payload: axios.get<IAssetType>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAssetType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ASSETTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAssetType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ASSETTYPE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAssetType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ASSETTYPE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAssetType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ASSETTYPE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
