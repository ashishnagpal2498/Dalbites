import React, { useState, useEffect } from "react";
import { NavigationContainer } from "@react-navigation/native";

import WelcomeStack from "./src/Components/WelcomeStack";
import Loading from "./screens/Loading";
import HomeTabGroup from "./src/Components/HomeTabGroup";

const App = () => {
  const [isLoading, setLoading] = useState(true);
  const [isAuth, setAuth] = useState(false);
  useEffect(() => {
    setTimeout(() => setLoading(false), 3000);
  });

  return (
    <NavigationContainer>
      {isLoading ? <Loading /> : isAuth ? <HomeTabGroup /> : <WelcomeStack />}
    </NavigationContainer>
  );
};

export default App;
