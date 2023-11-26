import React from "react";

import { Provider } from "react-redux";
import store from "./redux/store";
import AppStack from "./src/Components/AppStack";
import { StripeProvider } from '@stripe/stripe-react-native';

const stripePublishableKey = 'pk_test_51OE1QOAxY201a6THyYXNEE53UBpxbexVRdhos2k5vi1iBSmbzJ9KCaK7o8s6JvVATrRRkJvi1LlBGhnHCjHwHxfs00HXjdKrCO';

const App = () => {
  return (
    <StripeProvider publishableKey={stripePublishableKey}>
      <Provider store={store}>
        <AppStack />
      </Provider>
    </StripeProvider>
  );
};

export default App;
