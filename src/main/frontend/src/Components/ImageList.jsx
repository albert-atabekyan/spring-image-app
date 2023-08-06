import React from "react";
import "../css/imageList.css";
import ImageService from "../Service/ImageService";
import {API_URL} from "../Constants/APIConst";

const ImageList = ({images_id, setImages}) => {
    const staticUrl = API_URL;

    async function deleteImage(event) {
        event.preventDefault();

        const [image_id] = event.target.classList;

        await ImageService.deleteImage(image_id);

        const response = await ImageService.getImages();
        const images = response.data;
        setImages(images);

        return undefined;
    }

    const listItems = images_id.map((image) => {
        return <div key={image} className="image">
            <img src={staticUrl + "/image/" + image}></img>
            <button className={image} onClick={(event => deleteImage(event))}>Удалить</button>
        </div>
        }
    );

    return (
        <div className="imageList">
            {listItems}
        </div>
    );
};

export default ImageList;
