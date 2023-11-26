import React from "react";
import { View, ActivityIndicator, StyleSheet, Image } from "react-native";

const Loading = () => {
  return (
    <View style={styles.container}>
      <ActivityIndicator style={styles.indicator} size={100} color="black" />
      <Image
        style={styles.image}
        source={require("../assets/images/DalBites.png")}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  image: {
    width: 45,
    height: 45,
    flex: 1,
    alignSelf: "center",
    position: "absolute",
    resizeMode: "contain",
    zIndex: 1,
  },
  indicator: {
    zIndex: 3,
  },
});

export default Loading;
