import React from "react";
import { createDrawerNavigator } from "@react-navigation/drawer";
import Profile from "../../screens/Profile";
import CustomLogout from "../../screens/CustomLogout";
import RestaurantHomeTabGroup from "./RestaurantHomeTabGroup";

const Drawer = createDrawerNavigator();

const RestaurantSideDrawer = () => {

  return (
    <Drawer.Navigator screenOptions={{ headerTitle: "" }}>
      <Drawer.Screen name="RestaurantHomeStackGroup" component={RestaurantHomeTabGroup} />
      <Drawer.Screen name="Profile" component={Profile} />
      <Drawer.Screen name="Logout" component={CustomLogout} />
    </Drawer.Navigator>
  );
};

export default RestaurantSideDrawer;
