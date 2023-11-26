import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import IconTextBar from "../Layouts/IconTextBar";
import AddMenu from "../../screens/AddMenu";
import ReceivedOrder from "../../screens/ReceivedOrder";
import Colors from "../Utils/Colors";
import RestaurantProfile from "../../screens/RestaurantProfile";

const Tab = createBottomTabNavigator();

const RestaurantHome = () => (
  <Tab.Navigator
    screenOptions={({ route }) => ({
      headerStyle: {
        backgroundColor: "#EAB308",
      },
      tabBarIcon: ({ focused, color }) => {
        let iconName;
        if (route.name === "Menu") {
          iconType = "Ini";
          iconName = focused ? "menu" : "menu-outline";
        } else if (route.name === "Orders") {
          iconType = "Mci";
          iconName = "food";
        } else if (route.name === "Profile") {
          iconType = "Fai";
          iconName = focused ? "user-alt" : "user";
        }

        return (
          <IconTextBar
            iconName={iconName}
            iconType={iconType}
            iconOnly={true}
            iconColor={color}
          />
        );
      },
      tabBarActiveTintColor: Colors.primaryButton,
      tabBarInactiveTintColor: Colors.black,
    })}
  >
    <Tab.Screen name="Menu" component={AddMenu} />
    <Tab.Screen name="Orders" component={ReceivedOrder} />
    <Tab.Screen name="Profile" component={RestaurantProfile} />
  </Tab.Navigator>
);

export default RestaurantHome;
