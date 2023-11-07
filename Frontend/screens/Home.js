import React from "react";
import { View, Text, StyleSheet, Pressable } from "react-native";

const Home = ({ navigation }) => {
  return (
    <View style={styles.container}>
      <Text>Hello HomePage</Text>
      <Text>hello</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginTop: 25,
  },
});

export default Home;
