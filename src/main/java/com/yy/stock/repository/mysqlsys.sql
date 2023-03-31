show
variables like '%max_connections%';

show
global status like 'Max_used_connections';

set
GLOBAL max_connections=512;

show
global variables like 'wait_timeout';

set
global wait_timeout=60;

show
processlist;

set
global  interactive_timeout=60;

show
global variables like '%timeout%';