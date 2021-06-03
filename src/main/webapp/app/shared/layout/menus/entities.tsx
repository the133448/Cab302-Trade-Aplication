import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/asset-type">
      Asset Type
    </MenuItem>
    <MenuItem icon="asterisk" to="/asset-type-quantity">
      Asset Type Quantity
    </MenuItem>
    <MenuItem icon="asterisk" to="/application-user">
      Application User
    </MenuItem>
    <MenuItem icon="asterisk" to="/organisation">
      Organisation
    </MenuItem>
    <MenuItem icon="asterisk" to="/trade">
      Trade
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
