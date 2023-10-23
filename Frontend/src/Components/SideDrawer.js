import React from "react";
import { createDrawerNavigator } from "@react-navigation/drawer";
import Profile from "../../screens/Profile";
import CustomLogout from "../../screens/CustomLogout";
import HomeTabGroup from "./HomeTabGroup";

const Drawer = createDrawerNavigator();

const SideDrawer = () => {
  return (
    <Drawer.Navigator screenOptions={{ headerTitle: "" }}>
      <Drawer.Screen name="HomeStackGroup" component={HomeTabGroup} />
      <Drawer.Screen name="Profile" component={Profile} />
      <Drawer.Screen name="Logout" component={CustomLogout} />
    </Drawer.Navigator>
  );
};

export default SideDrawer;
