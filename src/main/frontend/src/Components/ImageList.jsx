import React from "react";
import "../css/imageList.css";
import ImageService from "../Service/ImageService";
import {ImageURL} from "../Constants/APIConst";

const ImageList = ({images, setImages}) => {
    const staticUrl = ImageURL;

    async function deleteImage(event) {
        event.preventDefault();

        const [url] = event.target.classList;
        const formData = new FormData();
        formData.append("url", url);

        await ImageService.deleteImage(formData);

        const response = await ImageService.getImage();
        const images = response.data;
        setImages(images);

        return undefined;
    }

    const listItems = images.map((image) => {
        return <div key={image.id} className="image">
            <img src={staticUrl.concat(image.url)} alt={image.url}/>
            <button className={image.url} onClick={(event => deleteImage(event))}>Удалить</button>
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
