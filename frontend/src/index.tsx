import React from 'react';
import ReactDOM from 'react-dom';
import smoothscroll from 'smoothscroll-polyfill';

// components
import App from './App';

// config
import './core/translations/i18n';

// global styles
import './styles/styles.scss';
import 'react-toastify/dist/ReactToastify.css';

smoothscroll.polyfill();

ReactDOM.render(<App />, document.getElementById('root'));
