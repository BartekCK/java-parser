import React from 'react';

type Props = {
    file: File;
};

const UploadPlaceWithFile: React.FC<Props> = (props: Props) => {
    const { file } = props;
    return <div>ala ma kota</div>;
};

export default UploadPlaceWithFile;
