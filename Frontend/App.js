import React from "react";

import { Provider } from "react-redux";
import store from "./redux/store";
import AppStack from "./src/Components/AppStack";

const App = () => {
  return (
    <Provider store={store}>
      <AppStack />
    </Provider>
  );
};

export default App;
