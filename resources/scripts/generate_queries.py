import json
import datetime

# convert timestamp to date
def convert_to_date(timestamp):
    date = datetime.datetime.fromtimestamp(timestamp).strftime('%Y-%m-%d')
    return date

# replace ' in string to ''
def preprocess(string):
    string = string.replace("'", "''")
    return string

# open source file and create project_queries.sql as output file
source_file = 'Kickstarter.json'
datasets = open(source_file, mode='r', encoding="utf-8")
output_file = 'project_queries.sql'
output = open(output_file, mode='w', encoding="utf-8")

# some missed projects
missed_projects = [1522047051,1769711891,96887393,78284316,1485116568,438853264]

# read through all dataset and generate sql queries
i = 0
while True:

    # store 20000 queries every file
    if i > 0 and i%20000 == 0:
        output.close()
        output_file = 'project_queries' + str(i//20000) + '.sql'
        output = open(output_file, mode='w', encoding="utf-8")

    # read one dataset
    line = datasets.readline()
    if not line: break # break loop if goes to the end
    dataset = json.loads(line)
    dataset = dataset['data']

    # search needed parameters
    project_id = dataset['id']
    name = preprocess(dataset['name'])
    blurb = preprocess(dataset['blurb'])
    photo = dataset['photo']['full']
    url = dataset['urls']['web']['project']
    currency = dataset['currency']
    goal = dataset['goal']
    launch_date = convert_to_date(dataset['created_at'])
    deadline = convert_to_date(dataset['deadline'])
    status = dataset['state']
    money_pledged = dataset['pledged']
    money_pledged_usd = dataset['usd_pledged']
    owner_id = dataset['creator']['id']
    if not 'location' in dataset.keys():
        location_id = 'NULL'
    else:
        location_id = dataset['location']['id']
    subcategory_id = dataset['category']['id']
    
    # write queries to project_queries.txt
    query = "insert into projects(project_id,name,blurb,photo,url,currency,\
goal,launch_date,deadline,status,money_pledged,money_pledged_usd,owner_id,lo\
cation_id,subcategory_id) values(" + str(project_id) + ",'" + name + "','" \
+ blurb + "','" + photo + "','" + url + "','" + currency + "'," + str(goal) \
+ ",to_date('" + launch_date + "','YYYY-MM-DD'),to_date('" + deadline + \
"','YYYY-MM-DD'),'" + status + "'," + str(money_pledged) + "," \
+ str(money_pledged_usd) + "," + str(owner_id) + "," + str(location_id) + "," \
+ str(subcategory_id) + ");\n"
    if project_id in missed_projects:
        output.write(query)
        i += 1

# close input and output file
datasets.close()
output.close()
print(str(i) + " queries are generated!")
