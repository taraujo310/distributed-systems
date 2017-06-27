class Resource
  def initialize(filepath)
    @filepath = filepath
  end

  def write(message, id)
    file = open_file

    puts message
    file.puts message

    file.close
  end

  def read(id)
    file = open_file

    file.rewind
    read = file.readlines

    file.close
    read
  end

  private
  def open_file
    return File.open(@filepath, 'a+') if File.exists?(@filepath)
    File.new(@filepath, 'a+')
  end
end
