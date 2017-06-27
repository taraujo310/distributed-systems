require_relative 'data_manager.rb'

class Reader
  def initialize(args={})
    @@readers ||= 0
    @id = (@@readers += 1)
    @filepath = args[:filepath]
    @data_manager = DataManager.new(filepath: args[:filepath], strategy: args[:strategy])
  end

  def read
    read = @data_manager.read(@filepath, @id)
    read
  end
end
