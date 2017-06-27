require_relative 'data_manager.rb'

class Writer
  def initialize(args={})
    @@writers ||= 0
    @id = (@@writers += 1)
    @filepath = args[:filepath]
    @data_manager = DataManager.new(filepath: args[:filepath], strategy: args[:strategy])
  end

  def write(message)
    @data_manager.write(@filepath, message, @id)
  end
end
