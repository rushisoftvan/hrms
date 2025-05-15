// project import
import Routes from 'routes';
import ThemeCustomization from 'themes';
import ScrollTop from 'components/ScrollTop';
import 'primereact/resources/themes/lara-light-blue/theme.css'; // or another theme
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css'; // optional, but useful for layout utilities
import { Toast } from 'primereact/toast';
// ==============================|| APP - THEME, ROUTER, LOCAL  ||============================== //

const App = () => (
  <ThemeCustomization>
    <Toast ref={toastRef} />
    <ScrollTop>
      <Routes />
    </ScrollTop>

  </ThemeCustomization>
);

export default App;
