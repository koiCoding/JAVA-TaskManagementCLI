class Task {
    private int id;
    private String description;
    private String status;

    public Task(int id, String description, String status){
        this.id = id;
        this.description = description;
        this.status = status;
    }
    
    public void updateTask(String newDescription){
        this.description = newDescription;
    }

    public void updateStatus(String newStatus){
        this.status = newStatus;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public String getStatus(){
        return this.status;
    }

    public String toJSONString(){
        return "{\"id\":\"" + id + "\",\"description\":\"" + description + "\",\"status\":\"" + status + "\"}";
    }

    public static Task fromJSONString(String taskJSON){
        taskJSON = taskJSON.replaceAll("[{} \"]", "");
        String[] fields = taskJSON.split(",");
        int id = Integer.parseInt(fields[0].split(":")[1]);
        String description = fields[1].split(":")[1];
        String status = fields[2].split(":")[1];

        return new Task(id, description, status);
    }
}
