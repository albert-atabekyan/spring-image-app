import React from "react";
import ImageService from "../Service/ImageService";
import {useEffect, useState} from "react";
import "../css/imageItem.css";
import PopUp from "./PopUp";
import Form from "./Form";

const ImageItem = ({image_id, setImages}) => {
    const [url, setUrl] = useState(undefined)

    async function deleteImage(event) {
        event.preventDefault();

        const [image_id] = event.target.classList;

        await ImageService.deleteImage(image_id);

        const response = await ImageService.getImages();
        const images = response.data;
        setImages(images);
    }

    async function updateImageTitle(event) {
        event.preventDefault();

        const title = event.target.title.value;

        const formData = new FormData();

        formData.append("title", title);
        await ImageService.updateImageTitle(image_id, formData);

        const image_container = document.getElementById(image_id + "_div").childNodes
        for (let element of image_container) {
            if(element.classList[0] === "imageItemTitle") { 
                const response = await ImageService.getImageInfo(image_id);
                element.textContent = response.data.title;
            } 
        }
    }

    useEffect(() => {
        ImageService.getImage(image_id).then(response => {
            const blob = response.data;
            const url = URL.createObjectURL(blob);
            console.log(url)
            setUrl(url)
        });

        ImageService.getImageInfo(image_id).then(response => {
            const info = response.data;
            const title = document.createElement("div")

            title.textContent = info.title
            title.classList.add("imageItemTitle");

            const image_container = document.getElementById(image_id + "_div")
            const secondElement = image_container.children[0];

            image_container.insertBefore(title, secondElement.nextElementSibling);
        });
       }, []);


    const input = [
        {
            id: 1,
            name: "title",
            type: "text",
            placeholder: "Введите новый заголовок",
            errorMessage:
                "Пожалуйста, введите корректный заголовок.",
            label: "Новый заголовок",
            pattern: "^[A-Za-z0-9]{3,16}$",
            required: true,
        }
    ];

    const handleSubmit = async (e) => {
        e.preventDefault();
    };

    return <div key={image_id} id={image_id + "_div"} className="image">
            <img src={url} alt={image_id}></img>
            <div className="buttonsContainer">
                <button className={image_id + " imageItemButton"} onClick={(event => deleteImage(event))}>Удалить</button>
                <PopUp 
                    openText = "Редактировать"
                    >
                <Form 
                    title="Редактировать заголовок"
                    inputs={input}
                    values={"title"}
                    buttonTitle="Редактировать заголовок"
                    onSubmit={updateImageTitle}
                    >
                </Form>
                </PopUp>
            </div>
        </div>
}

export default ImageItem;
