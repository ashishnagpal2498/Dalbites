import React from "react";
import { View, Text, StyleSheet, TouchableHighlight } from "react-native";
import {
  Ionicons,
  MaterialCommunityIcons,
  FontAwesome5,
  AntDesign,
} from "@expo/vector-icons";
import Colors from "../Utils/Colors";

const NavIcon = ({ iconName, iconType }) => {
  switch (iconType) {
    case "Ini": // IonIcons
      return <Ionicons name={iconName} size={25} color="black" />;

    case "Mci": // Material Community Icons
      return <MaterialCommunityIcons name={iconName} size={25} color="black" />;

    case "Fai": // FontAwesomeIcon
      return <FontAwesome5 name={iconName} size={25} color="black" />;
    case "Ati":
      return <AntDesign name={iconName} size={25} color="black" />;
    default:
      return <MaterialCommunityIcons name={iconName} size={25} color="black" />;
  }
};

const IconTextBar = ({
  iconType,
  iconName,
  iconText = "",
  iconColor = "black",
  iconOnly = false,
}) => {
  if (iconOnly) return <NavIcon iconType={iconType} iconName={iconName} />;
  else
    return (
      <TouchableHighlight
        style={[styles.container, { backgroundColor: iconColor }]}
        onPress={() => navigation.navigate(iconText)}
      >
        <View>
          <NavIcon iconType={iconType} iconName={iconName} />
          <Text style={styles.iconText}>{iconText}</Text>
        </View>
      </TouchableHighlight>
    );
};

const styles = StyleSheet.create({
  container: {
    width: "100%",
    maxHeight: 70,
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "row",
    backgroundColor: Colors.primaryButton,
  },
});

export default IconTextBar;
