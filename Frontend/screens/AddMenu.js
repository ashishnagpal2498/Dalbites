import React, { useEffect, useState } from "react";
import {
  StyleSheet,
  View,
  ScrollView,
  Text,
  TouchableOpacity,
  Button,
} from "react-native";
import MenuItem from "../src/Components/MenuItem";
import { useDispatch, useSelector } from "react-redux";
import {
  addRestaurantMenuItem,
  deleteRestaurantMenuItem,
  getRestaurantMenus,
  updateRestaurantMenuItem,
} from "../redux/actions/RestaurantAction";
import tw from "twrnc";
import SampleMenuItemImg from "../assets/images/DalBites.png";
import Loading from "./Loading";
import Modal from "react-native-modal";

const AddMenu = ({ route }) => {
  // const { id } = route.params;
  let token = useSelector((store) => store.authentication.token);
  const restaurantId = useSelector(
    (store) => store.authentication.restaurantId
  );
  const restaurant = useSelector((store) => store.restaurant.restaurant);
  const restaurantMenus = useSelector(
    (store) => store.restaurant.restaurantMenus
  );
  const loading = useSelector((store) => store.restaurant.restaurantLoading);
  const dispatch = useDispatch();

  let menuItemTemplate = {
    id: "",
    name: "",
    menu_image: SampleMenuItemImg,
    description: "",
    prepTime: "",
    cost: "",
    isAvailable: false,
    isNew: true,
  };

  const [menu, setMenu] = useState([]);
  const [isModalVisible, setModalVisible] = useState(false);

  const toggleModal = () => {
    setModalVisible(!isModalVisible);
  };

  useEffect(() => {
    // payload - id and token
    dispatch(getRestaurantMenus({ id: restaurantId, token }));
    setMenu(restaurantMenus);
  }, []);

  const updateMenuItem = ({
    id,
    name,
    description,
    prepTime,
    cost,
    isAvailable,
    isNew,
    fileObj,
  }) => {
    const restaurant_id = restaurantId;
    if (isNew == true) {
      dispatch(
        addRestaurantMenuItem({
          token,
          name,
          description,
          time: prepTime,
          price: cost,
          is_available: isAvailable,
          restaurant_id,
          fileObj,
        })
      );
      toggleModal();
    } else {
      dispatch(
        updateRestaurantMenuItem({
          token,
          id,
          name,
          description,
          time: prepTime,
          price: cost,
          is_available: isAvailable,
          restaurant_id,
        })
      );
    }
    setMenu(restaurantMenus);
    // setMenu((prevMenu) =>
    // prevMenu.map((menuItem) =>
    //     menuItem.id === id ? { id, name, description, prepTime, cost, isAvailable, isNew } : menuItem
    // ));
  };

  const deleteMenuItem = ({ menuId, isNew }) => {
    if (isNew == undefined) {
      dispatch(
        deleteRestaurantMenuItem({ token, menuId, restaurantId: restaurantId })
      );
    }
    setMenu((prevMenu) =>
      prevMenu.filter((menuItem) => menuItem.id !== menuId)
    );
  };

  const addMenuItem = () => {
    let newMenuItem = menuItemTemplate;
    newMenuItem.id = Date.now();
    newMenuItem.isNew = true;
    setMenu((prevMenu) => [...prevMenu, newMenuItem]);
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <View style={tw`flex-1 justify-center items-center bg-white`}>
      {/* {menu.length > 0 && loading == false ? */}
      <ScrollView>
        {restaurantMenus && restaurantMenus.length == 0 ? (
          <Text
            style={tw`text-black text-lg font-semibold text-center justify-center content-center my-70`}
          >
            Your menu is empty. Please add it using Add Menu Item button below
          </Text>
        ) : (
          restaurantMenus.map((menuItem) => (
            <MenuItem
              key={menuItem.id}
              cardData={menuItem}
              onUpdateCard={updateMenuItem}
              onDeleteCard={deleteMenuItem}
            ></MenuItem>
          ))
        )}
      </ScrollView>
      {/* : <Text style={tw`text-black text-lg font-semibold`}>Nothing to show. Please add using the Add Menu Item button</Text>
        } */}
      <View style={styles.container}>
        <TouchableOpacity
          style={tw`bg-yellow-500 rounded-lg py-2 px-4`}
          onPress={toggleModal}
        >
          <Text style={tw`text-black text-lg font-semibold`}>
            Add Menu Item
          </Text>
        </TouchableOpacity>
      </View>
      <Modal isVisible={isModalVisible}>
        <View
          style={{
            flex: 1,
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <View style={{ width: 360, height: 460 }}>
            <MenuItem
              cardData={menuItemTemplate}
              onUpdateCard={updateMenuItem}
              onDeleteCard={deleteMenuItem}
            ></MenuItem>
            <Button title="Close" onPress={toggleModal} />
          </View>
        </View>
        {/* <View style={{ flex: 1 }}>
                <MenuItem cardData={menuItemTemplate} onUpdateCard={updateMenuItem} onDeleteCard={deleteMenuItem} 
                ></MenuItem>
                <Button title="Hide modal" onPress={toggleModal} />
                </View> */}
      </Modal>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    marginBottom: 20,
    marginTop: 10,
  },
});

export default AddMenu;
