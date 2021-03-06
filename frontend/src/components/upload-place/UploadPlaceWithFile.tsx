import React from 'react';

// components
import ExtensionListComponent from '../extension-list-component';

// hooks
import { useTranslation } from 'react-i18next';

// types
import { AllowFileType } from '../../core/types/enums';

// styles
import './styles.scss';

// assets
import JsonDoc from '../../assets/convert-extensions/json-doc.png';
import CsvDoc from '../../assets/convert-extensions/csv-doc.png';
import YamlDoc from '../../assets/convert-extensions/yaml-doc.png';
import XmlDoc from '../../assets/convert-extensions/xml-doc.png';
import { uploadFile } from '../../api/commands';

type Props = {
    file: File;
    setFile: React.Dispatch<File | null>;
};

const UploadPlaceWithFile: React.FC<Props> = (props: Props) => {
    const { file, setFile } = props;

    const { t } = useTranslation();

    const currentFile = React.useMemo((): React.ReactElement => {
        switch (file.type) {
            case AllowFileType.xml:
                return <img src={XmlDoc} className="single-extension" />;
            case AllowFileType.json:
                return <img src={JsonDoc} className="single-extension" />;
            case AllowFileType.csv:
                return <img src={CsvDoc} className="single-extension" />;
            default:
                return <img src={YamlDoc} className="single-extension" />;
        }
    }, [file]);

    const handleDeleteClick = (): void => {
        setFile(null);
    };

    const setFileExtension = React.useCallback((target: AllowFileType): string => {
        switch (target) {
            case AllowFileType.xml:
                return 'xml';
            case AllowFileType.json:
                return 'json';
            case AllowFileType.csv:
                return 'csv';
            default:
                return 'yml';
        }
    }, []);

    const handleConvertClick = async (target: AllowFileType): Promise<void> => {
        const result = await uploadFile(file, target);

        const url = window.URL.createObjectURL(new Blob([result.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', `${file.name.split('.')[0]}.${setFileExtension(target)}`); //or any other extension
        document.body.appendChild(link);
        link.click();
    };

    return (
        <div className="upload--container-ready">
            <i className="trash fa fa-trash-o" aria-hidden="true" onClick={handleDeleteClick} />
            {currentFile}
            <span>{file.name}</span>
            <span className="convert-msg">{t('common.convertTo')}:</span>
            <ExtensionListComponent handleClick={handleConvertClick} typesForExclude={[file.type]} />
        </div>
    );
};

export default UploadPlaceWithFile;
