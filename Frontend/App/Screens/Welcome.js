import { Ionicons } from "@expo/vector-icons";
import { StyleSheet, Text, View } from "react-native";

export default Welcome = (props) => {
  return (
    <View style={styles.container}>
      <Ionicons name="logo-amplify" />
      <Text> Welcome Screen </Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    width: "100",
    height: "100",
    alignItems: "center",
    backgroundColor: "yellow",
  },
});
