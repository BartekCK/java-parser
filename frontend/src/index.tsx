import React from 'react';
import ReactDOM from 'react-dom';

// components
import App from './App';

// config
import './core/translations/i18n';

// global styles
import './styles/styles.scss';
import 'react-toastify/dist/ReactToastify.css';

ReactDOM.render(<App />, document.getElementById('root'));
