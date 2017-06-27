require_relative 'reader.rb'
require_relative 'writer.rb'

class Controller
  def initialize(strategy)
    @strategy = strategy
  end

  def write(filepath, value)
    w = Writer.new(filepath: filepath, strategy: @strategy)
    w.write value
  end

  def read(filepath)
    r = Reader.new(filepath: filepath, strategy: @strategy)
    r.read
  end
end
