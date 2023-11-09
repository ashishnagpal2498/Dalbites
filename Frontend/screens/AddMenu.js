import React, { useEffect, useState } from "react";
import { StyleSheet, View, Pressable, ScrollView, Text, TouchableOpacity, FlatList, Image } from 'react-native';
import MenuItem from "../src/Components/MenuItem";
import { useDispatch, useSelector } from "react-redux";
import {
    addRestaurantMenuItem,
    deleteRestaurantMenuItem,
    getRestaurantMenus,
    updateRestaurantMenuItem
  } from "../redux/actions/RestaurantAction";
import tw from "twrnc";
import SampleMenuItemImg from '../assets/images/DalBites.png';
import Loading from "./Loading";
import Modal from "react-native-modal";

const AddMenu = ({ route }) => {
    const { id } = route.params;
    let token = useSelector((store) => store.authentication.token);
    token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVXNlciIsInJvbGVfaWQiOjEsIm5hbWUiOiJSdXNoaSIsImV4cCI6MTcwMTkyMTYwMCwiaWF0IjoxNjk5MzkxMTAyLCJlbWFpbCI6InJ1c2hpcm9ubWVzQGdtYWlsLmNvbSIsInVzZXJuYW1lIjoiQjAwMDA3MDA3In0.vzmKvIwrIapAMt5nIJdwh9hUQfFsr3kuNq1MKJqkH9c";
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
        isNew: false
    };

    const [menu, setMenu] = useState([]);
    const [isModalVisible, setModalVisible] = useState(false);

    const toggleModal = () => {
        setModalVisible(!isModalVisible);
    };

    useEffect(() => {
        // payload - id and token
        dispatch(getRestaurantMenus({ id, token }));
        setMenu(restaurantMenus);
    }, []);

    // const handleDelete = (id) => {
    //     console.log("hello",id, menuItems.length);
    //     console.log(menuItems);
    //     setItems({menuItems: menuItems});
    //   };

    const updateMenuItem = ({ id, name, description, prepTime, cost, isAvailable, isNew, fileObj }) => {
        const restaurant_id = route.params.id;
        if(isNew == true){
            dispatch(addRestaurantMenuItem({ token, name, description, time: prepTime, price: cost, is_available: isAvailable, restaurant_id, fileObj}));
        } else{
            dispatch(updateRestaurantMenuItem({token, id, name, description, time: prepTime, price: cost, is_available: isAvailable, restaurant_id}));
        }
        setMenu(restaurantMenus);
        // setMenu((prevMenu) =>
        // prevMenu.map((menuItem) =>
        //     menuItem.id === id ? { id, name, description, prepTime, cost, isAvailable, isNew } : menuItem
        // ));
    };

      const deleteMenuItem = ({menuId, isNew}) => {
        const restaurantId = route.params.id;
        console.log(isNew);
        if(isNew == undefined){
            dispatch(deleteRestaurantMenuItem({token, menuId, restaurantId}));
        }
        setMenu((prevMenu) => prevMenu.filter((menuItem) => menuItem.id !== menuId));
      };

      const addMenuItem = () => {
        let newMenuItem = menuItemTemplate;
        newMenuItem.id = Date.now();
        newMenuItem.isNew = true;
        setMenu((prevMenu) => [...prevMenu, newMenuItem]);
      };

    
    // const addItem = () => {
    //     console.log("=============");
    //     console.log(typeof(menuItems));
    //     //menuItems.push(<MenuItem key={menuItems.length} arrayId={menuItems.length} onDelete={handleDelete}></MenuItem>);
    //     //menuItems.push(menuTemplate);
    //     let tmpMenuTemplate = menuTemplate;
    //     tmpMenuTemplate.key = menuItems.length+1;
    //     setItems([...menuItems, tmpMenuTemplate]);
    //     //setItems(menuItems);
    //     console.log(menuItems);
    // };
    

    const printData = () => {
        console.log(menu);
    }

    if (loading) {
        return <Loading />;
    }

    return(
        <View style={tw`flex-1 justify-center items-center bg-white`}>
        {menu.length > 0 && loading == false ?
            <ScrollView>
                {menu.length == 0 ? <Text style={tw`text-black text-lg font-semibold`}>Nothing to show. Please add using the Add Menu Item button</Text>:
                restaurantMenus.map((menuItem) => <MenuItem 
                key={menuItem.id} cardData={menuItem} onUpdateCard={updateMenuItem} onDeleteCard={deleteMenuItem} 
                ></MenuItem>)
                }
            </ScrollView> : <Text style={tw`text-black text-lg font-semibold`}>Nothing to show. Please add using the Add Menu Item button</Text>
        }
            <View style={styles.container}>
                <TouchableOpacity
                    style={tw`bg-yellow-500 rounded-lg py-2 px-4 mr-7`}
                    onPress={addMenuItem}
                >
                <Text style={tw`text-black text-lg font-semibold`}>Add Menu Item</Text>
                </TouchableOpacity>
                <TouchableOpacity
                    style={tw`bg-yellow-500 rounded-lg py-2 px-4`}
                    onPress={toggleModal}
                >
                <Text style={tw`text-black text-lg font-semibold`}>Save Menu</Text>
                </TouchableOpacity>
            </View>
            <Modal isVisible={isModalVisible}>
                <View style={{ flex: 1 }}>
                <Text>Hello!</Text>

                <Button title="Hide modal" onPress={toggleModal} />
                </View>
            </Modal>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        marginBottom: 20,
        marginTop: 10
    }
});

export default AddMenu;



// import React, { useState } from 'react';
// import Card from '../src/Components/MenuItem';
// import { Pressable, Text, View } from 'react-native';
// import tw from "twrnc";

// const MainScreen = () => {
//     const [cards, setCards] = useState([
//         { id: 1, name: 'Card 1', description: 'Description 1' },
//         { id: 2, name: 'Card 2', description: 'Description 2' },
//         // Add more cards as needed
//       ]);
    
//       const updateCard = ({ id, name, description }) => {
//         setCards((prevCards) =>
//           prevCards.map((card) =>
//             card.id === id ? { ...card, name, description } : card
//           )
//         );
//       };
    
//       const deleteCard = (cardId) => {
//         setCards((prevCards) => prevCards.filter((card) => card.id !== cardId));
//       };
    
//       const addCard = () => {
//         const newCard = { id: Date.now(), name: '', description: '' };
//         setCards((prevCards) => [...prevCards, newCard]);
//       };

//   const printData = () => {
//     console.log(cards);
// }

//   return (
//     <View className="main-screen">
//       {cards.map((cardData) => (
//         <Card key={cardData.id} cardData={cardData} onUpdateCard={updateCard}
//         onDeleteCard={deleteCard} />
//       ))}
//       <Pressable style={tw`bg-yellow-500 rounded-lg py-2 px-4`}
//       onPress={addCard}>
//         <Text style={tw`text-black text-lg font-semibold`}>Add Card</Text>
//         </Pressable>
//       <Pressable style={tw`bg-yellow-500 rounded-lg py-2 px-4`}
//       onPress={printData}>
//         <Text style={tw`text-black text-lg font-semibold`}>Print Data</Text>
//         </Pressable>
//     </View>
//   );
// };

// export default MainScreen;
